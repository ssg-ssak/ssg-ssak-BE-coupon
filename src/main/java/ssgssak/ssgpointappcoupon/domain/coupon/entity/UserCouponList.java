package ssgssak.ssgpointappcoupon.domain.coupon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserCouponList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "coupon_id")
    private Long couponId;

    @Column(nullable = false, name = "uuid")
    private String uuid;

    @Column(nullable = false, name = "download_date")
    private LocalDate downloadDate;

    @Column(nullable = false, name = "is_used")
    private Boolean isUsed;

    @Column(nullable = false, name = "is_expired")
    private Boolean isExpired;

    //todo: 쿠폰 사용시에 활성화
    public void updateIsUsedToTrue() {
        this.isUsed = true;
    }

    //todo: 쿠폰 만료시에 활성화
    public void updateIsExpiredToTrue() {
        this.isExpired = true;
    }
}
