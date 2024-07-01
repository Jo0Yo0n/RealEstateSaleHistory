package com.kosa.realestate.pagination.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class PageInfoDTO {

    private final int totalPosts;      // 전체 게시물 수
    private final int currentPage;     // 현재 페이지 번호
    private final int postsPerPage;    // 한 페이지당 게시물 수
    private final int displayPageNum;  // 한 번에 표시할 페이지 수

    private int startPage;
    private int endPage;
    private boolean hasPrev;
    private boolean hasNext;
    
    private int totalPages;

    
    public void updatePageInfo(int totalPages, int startPage, int endPage, boolean hasPrev, boolean hasNext) {
      this.totalPages = totalPages;
      this.startPage = startPage;
      this.endPage = endPage;
      this.hasPrev = hasPrev;
      this.hasNext = hasNext;
  }


}
