package com.stefanini.uhubank.converter;

public interface IApiConverter<E, D> {

	public E toEntidade(D dto);
	public D toDto(E entidade);
}
