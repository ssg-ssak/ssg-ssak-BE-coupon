package ssgssak.ssgpointappcoupon.domain.coupon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ssgssak.ssgpointappcoupon.global.common.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyCouponListData extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "coupon_id")
    private Long couponId;

    @Column(nullable = false, name = "uuid")
    private String uuid;

    @Column(nullable = false, name = "used", columnDefinition = "boolean default false")
    private Boolean used;

}
