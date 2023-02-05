package com.lc.reggie.Dto;


import com.lc.reggie.entity.Setmeal;
import com.lc.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
