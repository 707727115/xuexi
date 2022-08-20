package geektime.spring.data.mybatisdemo;

import com.github.pagehelper.PageInfo;
import geektime.spring.data.mybatisdemo.mapper.CoffeeMapper;
import geektime.spring.data.mybatisdemo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("geektime.spring.data.mybatisdemo.mapper")
//public class MybatisDemoApplication implements ApplicationRunner {
public class MybatisDemoApplication {
	@Autowired
	private CoffeeMapper coffeeMapper;

	public static void main(String[] args) {
		SpringApplication.run(MybatisDemoApplication.class, args);
	}

	/*@Override
	public void run(ApplicationArguments args) throws Exception {

		//分页查询
		coffeeMapper.findAllWithParam(1, 3)
				.forEach(c -> log.info("Page(1) Coffee {}", c));
		List<Coffee> list = coffeeMapper.findAllWithParam(2, 3);
		PageInfo page = new PageInfo(list);
		log.info("PageInfo: {}", page);

		//新增
		Coffee coffee = new Coffee();
		coffee.setName("黑咖啡");
		coffee.setPrice(Money.of(CurrencyUnit.USD,1.23));
		coffee =coffeeMapper.insert(coffee);
		log.info("新增: {}", coffee);
		//查询
		list =coffeeMapper.getAllCoffee();
		log.info("list: {}", list);
		//修改
		coffee.setId(Long.parseLong("1"));
		coffee.setName("白咖啡");
		coffee.setPrice(Money.of(CurrencyUnit.USD,2.23));
		coffeeMapper.update(coffee);
		coffee = coffeeMapper.getCoffeeById(Long.parseLong("1"));;
		log.info("修改后coffee: {}", coffee);
		//删除
		coffeeMapper.delete("1");
		list =coffeeMapper.getAllCoffee();
		log.info("删除id为1后list: {}", list);
	}*/
}

