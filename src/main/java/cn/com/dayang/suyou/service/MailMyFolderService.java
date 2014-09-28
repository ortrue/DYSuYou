package cn.com.dayang.suyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.dayang.suyou.repository.MailMyFolderDao;

@Component
@Transactional
public class MailMyFolderService {

	@Autowired
	private MailMyFolderDao mailMyFolderDao;
}
