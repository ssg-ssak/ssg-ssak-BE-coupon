package ssgssak.ssgpointappcoupon.domain.coupon.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.Coupon;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCouponNumber(String couponNumber);
}

