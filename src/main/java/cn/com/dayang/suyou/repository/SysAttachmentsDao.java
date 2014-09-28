package cn.com.dayang.suyou.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.dayang.suyou.entity.SysAttachments;

public interface SysAttachmentsDao extends PagingAndSortingRepository<SysAttachments, Integer>,JpaSpecificationExecutor<SysAttachments>{

}
