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
                "" +
  "rxVisitor=1742196397788FAQJ2NUCC4D92HBFVM6EFLF3CMTQJ8JM; LANGUAGE=ru; ID_TOKEN=; CHECK_AUTH=true; dtSa=-; JSESSIONID=2717AB46B9DDB5FB721BFEBA4F1D614C; ACCESS_TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJtUUNteHVDTzltT3lQNU56OTJXcHZrcUNWcjJDVlU5MHdKMi1veTYzcF9rIn0.eyJleHAiOjE3NDIyMTUwMDIsImlhdCI6MTc0MjIxNDEwMiwianRpIjoiMTJjZTRkNzItNGM2Yy00MjYzLTlhNDQtM2QxMTkyYmY0NTEwIiwiaXNzIjoiaHR0cHM6Ly9zZmVyYS1rZXljbG9hay1uZXcuaW5uby5sb2NhbC9hdXRoL3JlYWxtcy9zZmVyYS1zZXJ2aWNlcyIsInN1YiI6IlZUQjcwMTY2MDUyQGNvcnAuZGV2LnZ0YiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImNoYW5uZWwiLCJzaWQiOiIxNTk5OWQyMS04YTkwLTQ2MjUtYWFmMC0zNDc3NjU3YTAzNDMiLCJzY29wZSI6InByb2ZpbGUiLCJjaGFubmVsIjoic2ZlcmEiLCJuYW1lIjoi0JTQvNC40YLRgNC40Lkg0JHQvtGC0L7QsiIsInByZWZlcnJlZF91c2VybmFtZSI6InZ0YjcwMTY2MDUyIiwibWlkZGxlX25hbWUiOiLQnNC40YXQsNC50LvQvtCy0LjRhyIsImdpdmVuX25hbWUiOiLQlNC80LjRgtGA0LjQuSIsImZhbWlseV9uYW1lIjoi0JHQvtGC0L7QsiIsImVtYWlsIjoiZG1ib3RvdkBkZXYudnRiLnJ1IiwidXNlclByaW5jaXBhbE5hbWUiOiJWVEI3MDE2NjA1MkBjb3JwLmRldi52dGIifQ.LL7vjBZIX2YpkEp5rT-yJ47i0NJKTLSwYFaXkYStHCy1wbh0zS8IjoPcFjq8uHktw3j7QsSty2eHuQ3J7zx_1Q7FlHn58S-fk3vRahkm8pWwTa-hjJAKc4G9qqBfGD6VKDVDk7cI8beyJeeSEf_GYAYvVN6tlgp5gedw6z68Wrv0fVWn4zb2kJBaW_Hd_N6WQCvtZWSZVbizWRNsP26XCytksop9U_AyAm8xSn1btEtQPfXC0JHpurwrYOVKiUj6-6BbuCfk4YT4iGut-cQbbks_839omiCgKRnZre-C0tbeWOgAR3hO_KeOtlnKgFxWF1u25Sjb4GS-WWDC0TPzPQ; REFRESH_TOKEN=eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI4Y2U5YzAyMS1hNjk4LTQxYmQtODlmZi0xMGY4YjM0OTgzYjcifQ.eyJleHAiOjE3NDIyMjEzMDIsImlhdCI6MTc0MjIxNDEwMiwianRpIjoiMzNkMTE0NTUtYmNiYS00NTUwLWE2OWEtMTlhY2NkYWRlOTFhIiwiaXNzIjoiaHR0cHM6Ly9zZmVyYS1rZXljbG9hay1uZXcuaW5uby5sb2NhbC9hdXRoL3JlYWxtcy9zZmVyYS1zZXJ2aWNlcyIsImF1ZCI6Imh0dHBzOi8vc2ZlcmEta2V5Y2xvYWstbmV3Lmlubm8ubG9jYWwvYXV0aC9yZWFsbXMvc2ZlcmEtc2VydmljZXMiLCJzdWIiOiJWVEI3MDE2NjA1MkBjb3JwLmRldi52dGIiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoiY2hhbm5lbCIsInNpZCI6IjE1OTk5ZDIxLThhOTAtNDYyNS1hYWYwLTM0Nzc2NTdhMDM0MyIsInNjb3BlIjoiYmFzaWMgcHJvZmlsZSJ9.b23BvGKNGKB8tuiy6JStMfl0FMDQWY62vz9P-9CrKEiXRjGoUHmpKmBFjSnBw5RSH3MfNyHMhno_woky5ZwfKw; dtLatC=2; dtPC=20$414122489_106h-vVSFRBFWACOUPDMJMLWWUROIBRHFNMFRS-0e0; rxvt=1742215924525|1742209877502; dtCookie=v_4_srv_28_sn_009E439B78B3FE9F016BEAF7DE818E9E_perc_100000_ol_0_mul_1_app-3Aba15fcbff5d99f0e_1_app-3Af5beb900f30c572b_1"
             ).build();
        return chain.proceed(authenticatedRequest);
    }
}
