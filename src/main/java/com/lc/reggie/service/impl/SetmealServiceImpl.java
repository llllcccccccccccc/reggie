package com.lc.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.reggie.Dto.SetmealDto;
import com.lc.reggie.common.CustomException;
import com.lc.reggie.entity.Setmeal;
import com.lc.reggie.entity.SetmealDish;
import com.lc.reggie.service.SetmealDishService;
import com.lc.reggie.service.SetmealService;
import com.lc.reggie.mapper.SetmealMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ASUS
 * @description 针对表【setmeal(套餐)】的数据库操作Service实现
 * @createDate 2022-11-20 11:28:42
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
        implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;


    @Override
    @Transactional
    public void saveSetmealWithSetmealDish(SetmealDto setmealDto) {
        //保存套餐
        this.save(setmealDto);
        //保存对应关系数据
        saveSetmealDish(setmealDto);
    }


    @Transactional
    @Override
    public SetmealDto getSetmealDtoById(Long id) {
        SetmealDto setmealDto = new SetmealDto();

        Setmeal setmeal = this.getById(id);
        BeanUtils.copyProperties(setmeal, setmealDto);
        List<SetmealDish> list = setmealDishService.list(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, id));
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    @Override
    @Transactional
    public void updateSetmealWithSetmealDish(SetmealDto setmealDto) {
        //修改套餐信息
        this.updateById(setmealDto);

        //修改套餐对应的关系数据 步骤：删除旧的，添加新的
        setmealDishService.remove(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, setmealDto.getId()));

        this.saveSetmealDish(setmealDto);

    }

    @Transactional
    @Override
    public String deleteSetmealWithSetmealDish(List<Long> ids) {
        List<Setmeal> setmeals = this.listByIds(ids);
        for (Setmeal setmeal : setmeals) {
            if (setmeal.getStatus() == 1) {
                throw new CustomException("只允许删除停售的套餐！");
            }
        }

        //删除套餐
        this.removeByIds(ids);
        //删除对应关系数据
        for (int i = 0; i < ids.size(); i++) {
            setmealDishService.remove(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, ids.get(i)));
        }
        return "套餐删除成功！";
    }

    @Override
    public void updateStatus0(List<Long> ids) {
        for (Long id : ids) {
            Setmeal setmeal = this.getById(id);
            setmeal.setStatus(0);
            updateById(setmeal);
        }
    }

    @Override
    public void updateStatus1(List<Long> ids) {
        for (Long id : ids) {
            Setmeal setmeal = this.getById(id);
            setmeal.setStatus(1);
            updateById(setmeal);
        }
    }

    /**
     * 保存套餐对应关系数据
     */
    public void saveSetmealDish(SetmealDto setmealDto) {
        //获取套餐关系数据的对应套餐的信息
        Setmeal setmeal = this.getOne(new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getName, setmealDto.getName()));

        //保存套餐对应的菜品关系
        for (int i = 0; i < setmealDto.getSetmealDishes().size(); i++) {
            //获取id，赋值给其对应的套餐关系数据
            setmealDto.getSetmealDishes().get(i).setSetmealId(setmeal.getId());
        }
        //批处理，保存对应的关系数据
        setmealDishService.saveBatch(setmealDto.getSetmealDishes());
    }
}




