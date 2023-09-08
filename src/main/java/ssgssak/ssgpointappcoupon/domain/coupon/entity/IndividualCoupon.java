package ssgssak.ssgpointappcoupon.domain.coupon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssgssak.ssgpointappcoupon.global.common.entity.BaseTimeEntity;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IndividualCoupon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "coupon_id")
    private Long couponId;

    @Column(nullable = false, name = "coupon_number")
    private String couponNumber;

    @Column(nullable = false, name = "barcode_url")
    private String barcodeUrl;

    @Column(nullable = false, name = "is_registered")
    private Boolean isRegistered;

    @Column(name = "uuid")
    private String uuid;

    public void updateIsRegisteredToTrue() {
        this.isRegistered = true;
    }
    public void updateUuid(String uuid) {
        if(this.uuid == null) {
            this.uuid = uuid;
        }
    }
}
