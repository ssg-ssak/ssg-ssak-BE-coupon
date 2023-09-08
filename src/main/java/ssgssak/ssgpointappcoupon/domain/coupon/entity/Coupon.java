package ssgssak.ssgpointappcoupon.domain.coupon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

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

    @Column(nullable = false, name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(nullable = false, name = "expiration_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "brand")
    private String brand;

    @Column(nullable = false, name = "discount_image_url")
    private String discountImageUrl;

    @Column(nullable = false, name = "brand_image_url")
    private String brandImageUrl;

    @Column(nullable = false, name = "brand_logo_url")
    private String brandLogoUrl;

    @Column(nullable = false, name = "type")
    @Enumerated(EnumType.STRING)
    private CouponType type;
}