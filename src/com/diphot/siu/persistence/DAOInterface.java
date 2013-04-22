package com.diphot.siu.persistence;

import java.util.ArrayList;

import com.diphot.siuweb.shared.dtos.InterfaceDTO;

public interface DAOInterface<DTO extends InterfaceDTO> {
	void create(DTO dto);
	void massiveCreate(ArrayList<DTO> list);
	DTO findbyId(Long dto);
	ArrayList<DTO> getList();
	ArrayList<DTO> findbyParentID(Long id);
}