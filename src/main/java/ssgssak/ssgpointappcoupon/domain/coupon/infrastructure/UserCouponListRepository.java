package ssgssak.ssgpointappcoupon.domain.coupon.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.Coupon;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.UserCouponList;

import java.util.List;

public interface UserCouponListRepository extends JpaRepository<UserCouponList, Long> {
    UserCouponList findByUuidAndCouponId(String uuid, Long couponId);
    List<UserCouponList> findAllByUuidAndIsUsedFalseAndIsExpiredFalse(String uuid);
    List<UserCouponList> findAllByUuidAndIsUsedTrueOrIsExpiredTrue(String uuid);
    List<UserCouponList> findAllByUuidAndIsUsedTrue(String uuid);
    List<UserCouponList> findAllByUuidAndIsExpiredTrue(String uuid);

}
