package jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
@Embeddable
public class Peroid {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Peroid() { // 기본 생성자 필수
    }

    public Peroid(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
