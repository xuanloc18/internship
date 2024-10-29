package userService.test.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.mapping.Collection;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageRespone<T> {
    int currentPage;
    int totalPages;
    int pageSize;
    long totalElements;

    @Builder.Default
    private List<T> data = Collections.emptyList();
}
