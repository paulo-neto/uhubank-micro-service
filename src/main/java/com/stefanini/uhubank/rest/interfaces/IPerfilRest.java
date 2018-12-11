package com.stefanini.uhubank.rest.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.stefanini.uhubank.models.Perfil;
import com.stefanini.uhubank.rest.IRest;
import com.stefanini.uhubank.rest.dto.DTO;
import com.stefanini.uhubank.rest.dto.PerfilDTO;

@Path("perfis")
@Consumes(value = { MediaType.APPLICATION_JSON })
public interface IPerfilRest<T extends DTO<T>, ENTITY> extends IRest<PerfilDTO, Perfil> {

}
