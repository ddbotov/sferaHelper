package com.botov.sferaHelper.service;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request  = chain.request();
        Request authenticatedRequest = request.newBuilder().addHeader(
                "Cookie",
                "rxVisitor=1732521739228B1FNO8O8JF1HTQQ833SBN6EFURV0D2EF; LANGUAGE=ru; ID_TOKEN=; CHECK_AUTH=true; ACCESS_TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJtUUNteHVDTzltT3lQNU56OTJXcHZrcUNWcjJDVlU5MHdKMi1veTYzcF9rIn0.eyJleHAiOjE3MzMwNTA2MDIsImlhdCI6MTczMzA0OTcwMiwianRpIjoiODJhNGMwMTQtNDE2MC00Yzg4LWFmNTYtZDQwMWZlNjQ2NTU3IiwiaXNzIjoiaHR0cHM6Ly9zZmVyYS1rZXljbG9hay1uZXcuaW5uby5sb2NhbC9yZWFsbXMvc2ZlcmEtc2VydmljZXMiLCJzdWIiOiJWVEI3MDE2NjA1MkBjb3JwLmRldi52dGIiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjaGFubmVsIiwic2Vzc2lvbl9zdGF0ZSI6IjQ3YWRiZjAwLTViMjgtNDYwMC05OGI5LWFmMjcxNDdjNGE5YSIsInNjb3BlIjoicHJvZmlsZSIsInNpZCI6IjQ3YWRiZjAwLTViMjgtNDYwMC05OGI5LWFmMjcxNDdjNGE5YSIsImNoYW5uZWwiOiJzZmVyYSIsIm5hbWUiOiLQlNC80LjRgtGA0LjQuSDQkdC-0YLQvtCyIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidnRiNzAxNjYwNTIiLCJtaWRkbGVfbmFtZSI6ItCc0LjRhdCw0LnQu9C-0LLQuNGHIiwiZ2l2ZW5fbmFtZSI6ItCU0LzQuNGC0YDQuNC5IiwiZmFtaWx5X25hbWUiOiLQkdC-0YLQvtCyIiwiZW1haWwiOiJkbWJvdG92QGRldi52dGIucnUiLCJ1c2VyUHJpbmNpcGFsTmFtZSI6IlZUQjcwMTY2MDUyQGNvcnAuZGV2LnZ0YiJ9.ASjoWBk_XOZZq4gt669EWpbdyPjiQK5F5cjeaXsvIUNW2chqrKkVlHFFUQZm0UIDAYPOjQ-wrLOCiQo0tNZOZZsrT9piOo_o0MjdjbFslaY5b2xw7KeL3JVoF6qDJOf9SWV5K22OcGPBbCn8QQbA6RkF1jZrt5-bDYzP8AGlzwlI3zGR0P3njMS7TdfsjllwlvKHDVQZYyghTiwKR6MB7cN3m88j76TO_3-HaPhAVmpAljC1fWS6pAHUuXjB9XD_8QKdCRNIpDc28fQERLaa1QtXqXaF6IgPdxEVyifWwr85V6SkGg89crSpEg9oFNQN-SIeRHSEC-ibEYuwsu0E3A; REFRESH_TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzOTExODhmYi05ODBmLTQwYmQtYTE0ZS1hYjg4ODM3YWQzNWQifQ.eyJleHAiOjE3MzMwNTY5MDIsImlhdCI6MTczMzA0OTcwMiwianRpIjoiMzdhN2RlNjMtNTU5NC00ZTgwLTk2MjAtM2NlYmRhZjdhMTExIiwiaXNzIjoiaHR0cHM6Ly9zZmVyYS1rZXljbG9hay1uZXcuaW5uby5sb2NhbC9yZWFsbXMvc2ZlcmEtc2VydmljZXMiLCJhdWQiOiJodHRwczovL3NmZXJhLWtleWNsb2FrLW5ldy5pbm5vLmxvY2FsL3JlYWxtcy9zZmVyYS1zZXJ2aWNlcyIsInN1YiI6IlZUQjcwMTY2MDUyQGNvcnAuZGV2LnZ0YiIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJjaGFubmVsIiwic2Vzc2lvbl9zdGF0ZSI6IjQ3YWRiZjAwLTViMjgtNDYwMC05OGI5LWFmMjcxNDdjNGE5YSIsInNjb3BlIjoicHJvZmlsZSIsInNpZCI6IjQ3YWRiZjAwLTViMjgtNDYwMC05OGI5LWFmMjcxNDdjNGE5YSJ9._0seM0IJaYs8jZMfmTVqR4o_SdHUzDl7Jc48DVyC89U; dtSa=-; dtLatC=1; rxvt=1733052263603|1733048836181; dtPC=25$250462688_148h-vKUAADHJGDMATCPHIJPJHFKRSFHGCIPMC-0e0; JSESSIONID=F5140462B1CAF5E34DCED975C2D2DFF6; dtCookie=v_4_srv_25_sn_470DE047B9BC180AD2CCC93607EA7D5F_perc_100000_ol_0_mul_1_app-3Aba15fcbff5d99f0e_1"
        ).build();
        return chain.proceed(authenticatedRequest);
    }
}
