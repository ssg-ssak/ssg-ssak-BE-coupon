package ssgssak.ssgpointappcoupon.domain.coupon.vo;

import lombok.Getter;

@Getter
public class GetMyCouponsInputVo {
    private String uuid; //todo: 임시. 추후에 수정 요망.
    private String order; // new: 최신순, last: 마감임박순

    public GetMyCouponsInputVo(String uuid, String order) {
        this.uuid = uuid;
        this.order = order;
    }
}
