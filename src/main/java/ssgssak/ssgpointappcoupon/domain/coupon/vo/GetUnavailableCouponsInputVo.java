package ssgssak.ssgpointappcoupon.domain.coupon.vo;

import lombok.Getter;

@Getter
public class GetUnavailableCouponsInputVo {
    private String uuid; //todo: 임시
    private String status; // used: 사용완료, expired: 기간만료

    public GetUnavailableCouponsInputVo(String uuid, String status) {
        this.uuid = uuid;
        this.status = status;
    }
}
