package com.botov.sferaHelper.service;

import com.botov.sferaHelper.dto.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.util.List;

public interface SferaService {

    public static final String SFERA_BASE_URL = "https://sfera.inno.local/";
    public static final String SFERA_TICKET_START_PATH = SFERA_BASE_URL + "tasks/task/";

    SferaService INSTANCE = createSferaService();

    private static SferaService createSferaService() {
        var client = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SFERA_BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .client(client)
                .build();

        return retrofit.create(SferaService.class);
    }

    @GET("app/tasks/api/v0.1/entities" )
    Call<ListTicketsDto> listTicketsByQuery(@Query("query") String query, @Query("size") int size, @Query("page") int page);

    @PATCH("app/tasks/api/v0.1/entities/{number}")
    Call<Void> patchTicket(@Path("number") String number, @Body PatchTicketDto estimation);

    @PATCH("app/tasks/api/v1/entities/{number}")
    Call<Void> patchTicket2(@Path("number") String number, @Body PatchTicketDto estimation);

    @GET("app/tasks/api/v1/entity-views/{number}")
    Call<GetTicketDto> getTicket(@Path("number") String number);

    @HTTP(method = "DELETE", path = "app/tasks/api/v1/entities/attributes", hasBody = true)
    Call<Void> deleteAttributes(@Body List<AttributesDto> attributes);

    @POST("app/tasks/api/v1/entities/copy")
    Call<TicketCopyResponseDto> copyTicket(@Body TicketCopyRequestDto requestDto);

}
