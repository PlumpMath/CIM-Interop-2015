package org.endeavourhealth.cim.management.endpoints;

import org.endeavourhealth.core.repository.user.data.UserRepository;
import org.endeavourhealth.core.serializer.JsonSerializer;
import org.endeavourhealth.cim.management.model.User;
import org.endeavourhealth.cim.management.model.UserResponse;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/user")
public class UserEndpoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(User user) {
        try {
            if (user == null)
                return buildResponse(Response.Status.BAD_REQUEST);

            if (!isEmailAddressValid(user.getEmail()))
                return buildResponse(Response.Status.BAD_REQUEST);

            UserRepository userRepository = new UserRepository();

            if (userRepository.getByEmail(user.getEmail()) != null)
                return buildResponse(Response.Status.CONFLICT);

            String apikey = generateRandomString(128);
            String secret = generateRandomString(256);

            userRepository.add(apikey, secret, user.getEmail(), JsonSerializer.serialize(user), "0.1");

            UserResponse userResponse = new UserResponse();
            userResponse.setApikey(apikey);
            userResponse.setSecret(secret);
            userResponse.setEmail(user.getEmail());

            return buildResponse(Response.Status.CREATED, userResponse);
        } catch (Exception e) {
            return buildResponse(Response.Status.SERVICE_UNAVAILABLE);
        }
    }

    private static Response buildResponse(final Response.Status statusCode) {
        return Response.status(statusCode).build();
    }

    private static Response buildResponse(final Response.Status statusCode, final Object entity) {
        return Response
                .status(statusCode)
                .entity(entity)
                .build();
    }

    private static String generateRandomString(final int length) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(length);
        SecretKey secretKey = keyGen.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return DatatypeConverter.printHexBinary(encoded).toLowerCase();
    }

    public boolean isEmailAddressValid(final String hex) {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
    }
}
