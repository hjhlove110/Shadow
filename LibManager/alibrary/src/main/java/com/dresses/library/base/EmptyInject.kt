package com.dresses.library.base

import javax.inject.Inject



//解决dagger 重复在其他module创建 XX_MembersInject
class EmptyInject
@Inject
constructor() {

}
