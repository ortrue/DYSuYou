package cn.com.dayang.suyou.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.dayang.suyou.entity.SysPackage;

public interface SysPackageDao extends PagingAndSortingRepository<SysPackage, Integer>,JpaSpecificationExecutor<SysPackage>{

}
