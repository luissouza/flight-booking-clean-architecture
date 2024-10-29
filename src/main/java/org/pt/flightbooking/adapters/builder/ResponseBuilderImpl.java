package org.pt.flightbooking.adapters.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.pt.flightbooking.adapters.dto.Meta;
import org.pt.flightbooking.adapters.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilderImpl implements ResponseBuilder {

    @Autowired
    private HttpServletRequest request;

    private static final int RECORDS_PER_PAGE_DEFAULT = 10;
    private static final int TOTAL_PAGES_DEFAULT = 1;
    private static final int CURRENT_PAGE_DEFAULT = 1;
    private static final int TOTAL_RECORDS_DEFAULT = 0;

    public Meta metaDefaultBuilder() {
        return Meta.builder().totalPages(TOTAL_PAGES_DEFAULT)
                             .page(CURRENT_PAGE_DEFAULT)
                             .recordsPerPage(RECORDS_PER_PAGE_DEFAULT)
                             .totalRecords(TOTAL_RECORDS_DEFAULT).build();
    }

    public ResponseEntity<Object> build(Object data, long timeStamp, HttpStatus status) {

        Response<Object> response = new Response<>();
        Meta meta = metaDefaultBuilder();
        meta.setVersion("0.0.1");

        if (request != null && request.getParameter("page") != null) {
            meta.setPage(Integer.parseInt(request.getParameter("page")));
        }

        if (request != null && request.getParameter("rpp") != null) {
            meta.setRecordsPerPage(Integer.parseInt(request.getParameter("rpp")));
        }

        if((data instanceof Page<?>) ) {
            meta.setTotalRecords((int) ((Page<?>) data).getTotalElements());
            meta.setTotalPages(((Page<?>) data).getTotalPages());
            response.setData(((Page<?>) data).getContent());
        }

        if ((data instanceof List<?>) ) {
            List<?> list = (List) data;
            meta.setTotalRecords(list.size());
            meta.setTotalPages((int) Math.ceil((double) meta.getTotalRecords() / meta.getRecordsPerPage()));

            int start = meta.getRecordsPerPage() * (meta.getPage()-1);
            int end = meta.getPage() * meta.getRecordsPerPage();
            if(end > meta.getTotalRecords()) {
                end = meta.getTotalRecords();
            }

            ArrayList<Object> pagedResponse = new ArrayList<>(list.subList(start, end));
            response.setData(pagedResponse);
        }

        if(response.getData()== null) response.setData(data);

        meta.setDuration(new Date().getTime() - timeStamp);
        response.setMeta(meta);

        return new ResponseEntity<>(response, status);
    }
}