package geektime.spring.data.mybatisdemo.mapper;

import geektime.spring.data.mybatisdemo.model.Coffee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface CoffeeMapper {
    @Select("select * from t_coffee order by id")
    List<Coffee> findAllWithRowBounds(RowBounds rowBounds);

    @Select("select * from t_coffee order by id")
    List<Coffee> findAllWithParam(@Param("pageNum") int pageNum,
                                  @Param("pageSize") int pageSize);


    @Select("select * from t_coffee")
    List<Coffee> getAllCoffee();

    @Select("select * from t_coffee where id=#{id}")
    Coffee getCoffeeById(Long id);

    @Select("select * from t_coffee where name=#{name}")
    Coffee getCoffeeByName(String name);

    @Insert("insert into t_coffee (name,price,create_time,update_time) values (#{name},#{price},now(),now())")
    Coffee insert(Coffee coffee);

    @Update("update t_coffee set name=#{name},price=#{price},update_time=now() where id=#{id}")
    Coffee updateCoffee (Coffee coffee);

    @Delete("delete from t_coffee where id=#{id}")
    Coffee delete (Long id);




}
