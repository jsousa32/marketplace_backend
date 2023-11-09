package com.example.marketpalce.utils;

import com.example.marketpalce.model.dtos.Usuario;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;

import java.util.*;

/**
 * The Class MapperUtils
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
public class MapperUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <T> T mapper(Object source, Class<T> result) {
        return modelMapper.map(source, result);
    }

    public static UserRepresentation userRepresentantion(Usuario usuario) {
        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setFirstName(usuario.getNome());
        userRepresentation.setLastName(usuario.getSobrenome());
        userRepresentation.setEmail(usuario.getEmail());
        userRepresentation.setUsername(usuario.getEmail());
        userRepresentation.setEmailVerified(false);
        userRepresentation.setEnabled(!usuario.getDisabled());
        userRepresentation.setAttributes(attributes("telefone", usuario.getTelefone()));
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation(usuario.getSenha())));

        return userRepresentation;
    }

    public static CredentialRepresentation credentialRepresentation(String senha) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(senha);

        return credentialRepresentation;
    }

    public static Map<String, List<String>> attributes(String attributeName, String value) {
        HashMap<String, List<String>> attributes = new HashMap<>();

        attributes.put(attributeName, Collections.singletonList(value));

        return attributes;
    }
}
