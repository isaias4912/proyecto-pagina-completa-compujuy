package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.SysDAO;
import ar.com.jsuper.domain.Message;
import ar.com.jsuper.dto.MessageDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.SysService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysServiceImpl implements SysService {

	@Autowired
	SysDAO sysDAO;
	@Autowired
	private OrikaBeanMapper modelMapper;

	@Override
	@Transactional()
	public MessageDTO insert(MessageDTO messageDTO) {
		Message message = modelMapper.map(messageDTO, Message.class);
		message.setFechaCarga(new Date());
		sysDAO.insert(message);
		return modelMapper.map(message, MessageDTO.class);
	}

}
