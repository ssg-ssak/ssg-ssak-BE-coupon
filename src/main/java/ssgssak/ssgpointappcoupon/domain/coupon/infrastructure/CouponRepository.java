package ssgssak.ssgpointappcoupon.domain.coupon.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.Coupon;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.CouponType;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.UserCouponList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findAllByTypeAndStartDateLessThanEqualAndExpirationDateGreaterThanEqual(
            CouponType downloadable, LocalDate today1, LocalDate today2);

    Optional<Coupon> findFirstByIdAndTypeAndStartDateLessThanEqualAndExpirationDateGreaterThanEqual(
            Long id, CouponType registrable, LocalDate today1, LocalDate today2);
}