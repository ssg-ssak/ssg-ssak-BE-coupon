package ssgssak.ssgpointappcoupon.domain.coupon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicUpdate
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "coupon_name")
    private String couponName;

    // 다운로드형, 등록형
    @Column(nullable = false, name = "counpon_type")
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Column(nullable = false, name = "coupon_number")
    private String couponNumber;

    @Column(nullable = false, name = "barcode_url")
    private String barcodeUrl;

    // 사용가능일자
    @Column(nullable = false, name = "coupon_issue_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate couponIssueDate;

    // 쿠폰 만료일
    @Column(nullable = false, name = "coupon_expiration_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate couponExpirationDate;

    // 사용가능한 가맹점
    @Column(nullable = false, name = "available_shop")
    private String availableShop;

    @Column(nullable = false, name = "coupon_image_url")
    private String couponImageUrl;

    // 등록여부
    @Column(nullable = false, name = "is_registered")
    private Boolean isRegistered;

    // 쿠폰 등록 여부 true로 변경
    public void updateCouponStatus(){
        this.isRegistered = true;
    }
}