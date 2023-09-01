/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

/**
 *
 * @author rafa
 */
public class GenericDataDTO<T> {

	private T data;

	public GenericDataDTO(T data) {
		this.data = data;
	}

	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
