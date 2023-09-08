package ssgssak.ssgpointappcoupon.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadCouponOutputDto {
    private Long id;
    private Long couponId;
    private String uuid;
    private LocalDate downloadDate;
    private Boolean isUsed;
    private Boolean isExpired;
}
