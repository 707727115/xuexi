package geektime.spring.data.mybatisdemo.controller;


import geektime.spring.data.mybatisdemo.controller.request.NewCoffeeRequest;
import geektime.spring.data.mybatisdemo.model.Coffee;
import geektime.spring.data.mybatisdemo.sequence.SnowflakeManager;
import geektime.spring.data.mybatisdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;
    //注入
    @Autowired
    private SnowflakeManager snowflakeManager;
    private long snowflakeID = 0;

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffeeWithoutBindingResult(@Valid NewCoffeeRequest newCoffee) {

        //全局唯一id

        try{
            snowflakeID = snowflakeManager.nextValue();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return coffeeService.saveCoffee(snowflakeID,newCoffee.getName(), newCoffee.getPrice());
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addJsonCoffeeWithoutBindingResult(@Valid @RequestBody NewCoffeeRequest newCoffee) {

        //全局唯一id
        long snowflakeID = 0;
        try{
            snowflakeID = snowflakeManager.nextValue();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return coffeeService.saveCoffee(snowflakeID,newCoffee.getName(), newCoffee.getPrice());
    }

    @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)

    public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file) {
        List<Coffee> coffees = new ArrayList<>();
        if (!file.isEmpty()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream()));
                String str;
                while ((str = reader.readLine()) != null) {
                    String[] arr = StringUtils.split(str, " ");
                    if (arr != null && arr.length == 2) {
                        //全局唯一id
                        try{
                            snowflakeID = snowflakeManager.nextValue();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        coffees.add(coffeeService.saveCoffee(snowflakeID,arr[0],
                                Money.of(CurrencyUnit.of("CNY"), 1)));
                    }
                }
            } catch (IOException e) {
                log.error("exception", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return coffees;
    }

    @GetMapping(path = "/", params = "!name")
    public List<Coffee> getAll() {
        return coffeeService.getAllCoffee();
    }

    @GetMapping("/{id}")
    public Coffee getById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffee(id);
        log.info("Coffee {}:", coffee);
        return coffee;
    }

    @GetMapping(path = "/", params = "name")
    public Coffee getByName(@RequestParam String name) {
        return coffeeService.getCoffee(name);
    }


    @PutMapping
    public Coffee updateById(@RequestParam Long id,@RequestParam String name,@RequestParam String price) {
        return coffeeService.updateCoffee(id,name,Money.of(CurrencyUnit.of("CNY"), Double.parseDouble(price)));
    }

    @DeleteMapping
    public Coffee deleteById(@RequestParam Long id) {
        //一般返回的json包括code和resMsg。作业重点不在于这
        return coffeeService.deleteCoffee(id);
    }


}
