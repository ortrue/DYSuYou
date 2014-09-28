package cn.com.dayang.suyou.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.dayang.suyou.entity.SysMails;

public interface SysMailsDao extends PagingAndSortingRepository<SysMails, Integer>,JpaSpecificationExecutor<SysMails>{

}
