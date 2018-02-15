package go.id.payakumbuh.siwarta.impl;

import go.id.payakumbuh.siwarta.db.models.login.BaseUser;

/**
 * Created by anggrayudi on 08/02/18.
 */
public interface UserImpl<T extends BaseUser> {

    T getUser();
}
