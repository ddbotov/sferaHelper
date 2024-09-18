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
                "rxVisitor=1724653208039VESOENF173Q28AJKLDUUSBE7E4V1V0PF; NX-ANTI-CSRF-TOKEN=0.3529637470269442; CHECK_AUTH=true; LANGUAGE=ru; ACCESS_TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJtUUNteHVDTzltT3lQNU56OTJXcHZrcUNWcjJDVlU5MHdKMi1veTYzcF9rIn0.eyJleHAiOjE3MjY2NjY2NjAsImlhdCI6MTcyNjY2NTc2MCwianRpIjoiNWQ1ZDcxMDgtZTg5Yy00YWJmLTgyMDUtY2FmZTQzN2ZjZDE5IiwiaXNzIjoiaHR0cHM6Ly9zZmVyYS1rZXljbG9hay1uZXcuaW5uby5sb2NhbC9yZWFsbXMvc2ZlcmEtc2VydmljZXMiLCJzdWIiOiJWVEI3MDE2NjA1MkBjb3JwLmRldi52dGIiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjaGFubmVsIiwic2Vzc2lvbl9zdGF0ZSI6ImVhMThmYjU1LWNkODEtNGY2NC1hN2E2LTQyNDU2NjEyZGE5MyIsInNjb3BlIjoicHJvZmlsZSIsInNpZCI6ImVhMThmYjU1LWNkODEtNGY2NC1hN2E2LTQyNDU2NjEyZGE5MyIsImNoYW5uZWwiOiJzZmVyYSIsIm5hbWUiOiLQlNC80LjRgtGA0LjQuSDQkdC-0YLQvtCyIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidnRiNzAxNjYwNTIiLCJtaWRkbGVfbmFtZSI6ItCc0LjRhdCw0LnQu9C-0LLQuNGHIiwiZ2l2ZW5fbmFtZSI6ItCU0LzQuNGC0YDQuNC5IiwiZmFtaWx5X25hbWUiOiLQkdC-0YLQvtCyIiwiZW1haWwiOiJkbWJvdG92QGRldi52dGIucnUiLCJ1c2VyUHJpbmNpcGFsTmFtZSI6IlZUQjcwMTY2MDUyQGNvcnAuZGV2LnZ0YiJ9.P-SkiIuVVIYQ2-T9w4wp58GwptvEwoYMLKlqofseFtg4_KPb-UDsW3k03J4xDKGq0BbUlUR8R6-JO_HB-o7rtrFFKAaPIgPOe1tdLcC4Ls9iINaT9mxFr03MrApOs3wueMcG3kpv9rfZrEAyRPBsQ3mVuJzc_ohpjSsyuaOLF6PvrdGGEe3iH3BmyLVs5yHwq2Cd72AV4nY5qtqsPkZwsIExJKD2BxoP662OO7n06OFxUlD0jZvu6RKxE4Ox7MUMCmiMWwa3hgOBuJUD4Nv3V4udfb3VggLslcnbCJHLZrPc01t2yXaXhQLwecIXQ74KSRn6w79FLsuQMNdTVfs0Pg; REFRESH_TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzOTExODhmYi05ODBmLTQwYmQtYTE0ZS1hYjg4ODM3YWQzNWQifQ.eyJleHAiOjE3MjY2NzI5NjAsImlhdCI6MTcyNjY2NTc2MCwianRpIjoiYzNlMTEzOWQtZTA1Zi00NDczLTk0MzAtYjc2Mzg4M2M5NmY2IiwiaXNzIjoiaHR0cHM6Ly9zZmVyYS1rZXljbG9hay1uZXcuaW5uby5sb2NhbC9yZWFsbXMvc2ZlcmEtc2VydmljZXMiLCJhdWQiOiJodHRwczovL3NmZXJhLWtleWNsb2FrLW5ldy5pbm5vLmxvY2FsL3JlYWxtcy9zZmVyYS1zZXJ2aWNlcyIsInN1YiI6IlZUQjcwMTY2MDUyQGNvcnAuZGV2LnZ0YiIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJjaGFubmVsIiwic2Vzc2lvbl9zdGF0ZSI6ImVhMThmYjU1LWNkODEtNGY2NC1hN2E2LTQyNDU2NjEyZGE5MyIsInNjb3BlIjoicHJvZmlsZSIsInNpZCI6ImVhMThmYjU1LWNkODEtNGY2NC1hN2E2LTQyNDU2NjEyZGE5MyJ9.JRqEljJdQNbwOrJsXz8B0405ydRCnTmZlWQpanrCcQQ; dtSa=-; dtLatC=3; dtCookie=v_4_srv_30_sn_4F899E74907C98FFA27BA2DFEC090901_perc_100000_ol_0_mul_1_app-3Aba15fcbff5d99f0e_1; JSESSIONID=00E78B7E9A4892F41A8B38F9D9F2C934; rxvt=1726668126954|1726663398241; dtPC=30$466203073_208h27vTCHTDGAHFCUMLPITBDTTOPQMURHIVVMA-0e0")
                .build();
        return chain.proceed(authenticatedRequest);
    }
}
