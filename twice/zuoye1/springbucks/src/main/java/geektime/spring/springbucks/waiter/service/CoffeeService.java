package geektime.spring.springbucks.waiter.service;

import geektime.spring.springbucks.waiter.model.Coffee;
import geektime.spring.springbucks.waiter.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {


    //使用注解可以不需要定义set方法，直接通过反射就自动的配置好了
    //添加@Autowired注解，对Eat类进行自动装配，此时原理就是通过byName实现的，要是属性ID没有注入到spring容器中就会报错
    //使用@Resource注解，默认是byName，要是没找到就再通过byType，此时要是再存在多个同一类型的属性ID，则byName查找失败，就会报错
    //指定属性ID @Resource(name ="coffeeOrderService1")，显示的指定bean的名字
    @Resource(name ="coffeeOrderService1")
    private CoffeeOrderService coffeeOrderService
    //当 @Autowired(required =false)定义时，将该required属性设置为false表示该属性不需要用于自动装配，如果不能自动装配该属性将被忽略
    //此时属性值就可以为null了
    //还有一种方式可以设置属性为null，在方法传递参数前添加一个@Nullable注解，表示该参数可以为null
    //@Autowired 注解要是想实现指定的Bean就要配合另一个注解(@Qualifier)
    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee saveCoffee(String name, Money price) {
        return coffeeRepository.save(Coffee.builder().name(name).price(price).build());
    }

    @Cacheable
    public List<Coffee> getAllCoffee() {
        return coffeeRepository.findAll(Sort.by("id"));
    }

    public Coffee getCoffee(Long id) {
//        return coffeeRepository.findById(id).get();
        return coffeeRepository.getOne(id);
    }

    public Coffee getCoffee(String name) {
        return coffeeRepository.findByName(name);
    }

    public List<Coffee> getCoffeeByName(List<String> names) {
        return coffeeRepository.findByNameInOrderById(names);
    }
}
