package cn.acyou.scorpio.controller.slave;

import cn.acyou.framework.model.Result;
import cn.acyou.scorpio.salve.entity.StudentSlave;
import cn.acyou.scorpio.service.slave.StudentSlaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2021/1/22]
 **/
@Slf4j
@RestController
@RequestMapping("/Student多数据源测试")
@Api(value = "Student多数据源测试", description = "StudentSlave", tags = "Student多数据源测试")
public class StudentSlaveController {
    @Autowired
    private StudentSlaveService studentSlaveService;

    @GetMapping(value = "selectAll")
    @ApiOperation("selectAll")
    public Result<List<StudentSlave>> selectAll(){
        List<StudentSlave> studentSalves = studentSlaveService.selectStudents();
        return Result.success(studentSalves);
    }
}
