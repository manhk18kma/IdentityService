package TTCS.Security.controller;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class ProfileCreateRequest {
    String username;
    String fullName;
    String biography;
    String profilePictureUrl;
    String websiteUrl;
    Date dateOfBirth;
    String address;
    String userId;
}
