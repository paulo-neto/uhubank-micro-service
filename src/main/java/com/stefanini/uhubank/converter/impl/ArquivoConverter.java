package com.stefanini.uhubank.converter.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.stefanini.uhubank.converter.IApiConverter;
import com.stefanini.uhubank.models.Arquivo;

public class ArquivoConverter implements IApiConverter<Arquivo, MultipartFormDataInput> {

	@Inject
	private Logger logger;
	
	@Override
	public Arquivo toEntidade(MultipartFormDataInput input) {
		Arquivo arquivo = new Arquivo();
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");

		for (InputPart inputPart : inputParts) {
			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				arquivo.setNome(getFileName(header));
				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				byte[] bytes = IOUtils.toByteArray(inputStream);
				arquivo.setConteudo(bytes);
				arquivo.setTipo(getExtension(arquivo.getNome()));
			} catch (IOException e) {
				logger.error(ExceptionUtils.getRootCauseMessage(e));
			}
			return arquivo;
		}
		return null;
	}

	@Override
	public MultipartFormDataInput toDto(Arquivo entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
	 * name="file"; filename="filename.extension"] }
	 **/
	// get uploaded filename, is there a easy way in RESTEasy?
	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
	
	private String getExtension(String nome) {
		return nome.split(".")[1];
	}
}
