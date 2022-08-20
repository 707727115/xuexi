package geektime.spring.data.mybatisdemo.service;


import geektime.spring.data.mybatisdemo.constant.CacheConstants;
import geektime.spring.data.mybatisdemo.mapper.CoffeeMapper;
import geektime.spring.data.mybatisdemo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@CacheConfig(cacheNames = "CoffeeCache")

public class CoffeeService {
    @Autowired
    private CoffeeMapper coffeeMapper;

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {CacheConstants.MENU_DETAILS}, allEntries = true)
    public Coffee saveCoffee(String name, Money price) {
        return coffeeMapper.insert(Coffee.builder().name(name).price(price).build());

    }
    @Cacheable(value = CacheConstants.MENU_DETAILS, key = "'coffee_menu'", unless = "#result == null")
    public List<Coffee> getAllCoffee() {
        return coffeeMapper.getAllCoffee();
    }

    public Coffee getCoffee(Long id) {
        return coffeeMapper.getCoffeeById(id);
    }

    public Coffee getCoffee(String name) {
        return coffeeMapper.getCoffeeByName(name);
    }

//    public List<Coffee> getCoffeeByName(List<String> names) {
//        return coffeeRepository.findByNameInOrderById(names);
//    }

    @Transactional(rollbackFor = Exception.class)
    public Coffee updateCoffee(Long id,String name,Money price) {
        return coffeeMapper.updateCoffee(Coffee.builder().name(name).price(price).id(id).build());
    }

    @Transactional(rollbackFor = Exception.class)
    public Coffee deleteCoffee(Long id) {
        return coffeeMapper.delete(id);
    }

}
