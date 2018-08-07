package com.kynchen.service.take_delivery;/*
 * @author kynchen
 *
 * @date 2018/8/3 21:59
 *
 * @version idea
 */

import bos.domain.page.PageBean;
import bos.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.*;
import java.util.Date;

public interface PromotionService {

    void save(Promotion model);

    Page<Promotion> findAll(Pageable pageable);

    @Path("/pageQuery")
    @GET
    @Produces({"application/xml","application/json"})
    PageBean<Promotion> findPageData(@QueryParam("page") int page, @QueryParam("rows") int rows);

    @Path("/promotion/{id}")
    @GET
    @Produces({"application/xml","application/json"})
    Promotion findById(@PathParam("id") Integer id);

    void updateDate(Date date);
}
