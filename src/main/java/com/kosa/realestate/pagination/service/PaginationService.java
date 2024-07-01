package com.kosa.realestate.pagination.service;

import org.springframework.stereotype.Service;
import com.kosa.realestate.pagination.dto.PageInfoDTO;

@Service
public class PaginationService {
  
  
  public void calculatePageInfo(PageInfoDTO pageInfo) {
    int totalPosts = pageInfo.getTotalPosts();
    int postsPerPage = pageInfo.getPostsPerPage();
    int currentPage = pageInfo.getCurrentPage();
    int displayPageNum = pageInfo.getDisplayPageNum();

    int totalPages = totalPages(totalPosts, postsPerPage);
    int startPage = startPage(currentPage, displayPageNum);
    int endPage = endPage(currentPage, displayPageNum, totalPages);
    boolean hasPrev = hasPrev(startPage);
    boolean hasNext = hasNext(endPage, totalPages);

    pageInfo.updatePageInfo(totalPages,startPage, endPage, hasPrev, hasNext);
}


    private int totalPages(int totalPosts, int postsPerPage) {
        return ((totalPosts - 1) / postsPerPage) + 1;
    }

    private int endPage(int currentPage, int displayPageNum, int totalPages) {
      int endPage = ((currentPage - 1) / displayPageNum + 1) * displayPageNum;
      return Math.min(endPage, totalPages);
  }

    private int startPage(int currentPage, int displayPageNum) {
        return ((currentPage - 1) / displayPageNum) * displayPageNum + 1;
    }

    private boolean hasPrev(int startPage) {
        return startPage != 1;
    }

    private boolean hasNext(int endPage, int totalPages) {
        return endPage != totalPages;
    }
}
