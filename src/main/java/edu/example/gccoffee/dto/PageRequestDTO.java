package edu.example.gccoffee.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequestDTO {
    //2. 필드 설명
    @Builder.Default
    @Min(1)
    @Max(100)
    private int page = 1; //페이지 번호 - 첫번째 페이지 0부터 시작

    //3. 필드 설명
    @Builder.Default
    @Min(10)
    @Max(100)
    private int size=10; //한 페이지 게시물 수

    //페이지번호, 페이지 게시물 수 , 정렬 순서를 Pageble 객체로 반환
    public Pageable getPageable(Sort sort) {
        int pageNum=page<0?1:page-1;
        int sizeNum=size<=10?10:size;

        return PageRequest.of(0,10,sort);
    }
}
