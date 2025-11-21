package com.springboot.dto;

public record MemberRequest(String username,
                            String password,
                            String name,
                            String email
                            ) {
}
