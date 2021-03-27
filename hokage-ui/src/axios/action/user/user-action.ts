/**
 * long description for the file
 * @summary short description for the file
 * @author linyimin <linyimin520812@gmail.com>
 * Created at     : 2020-10-27 00:55:23
 */

import { UserService } from '../../service'
import {
    UserLoginForm,
    UserLogoutForm,
    UserRegisterForm,
    UserServerSearchForm,
    UserServerOperateForm,
    UserVO,
} from './user-type';
import { Models } from '../../../utils/model'
import { ServiceResult } from '../../common'
import { removeHokageUid, setHokageUid } from '../../../utils'
import { OperatorSearchFormType } from '../../../components/user/operator/search';

 export const UserAction = {
     login: (formData: UserLoginForm): Promise<ServiceResult<UserRegisterForm>> => {
        return new Promise((resolve, reject) => {
            UserService.login(formData).then(value => {
                if (value.success && value.data) {
                    const data: UserRegisterForm = value.data
                    setHokageUid(data.id || 0)
					return resolve(value)
                }
                
                return reject(value)
				
            }).catch(err => {
				return reject(err)
			})
        })
     },

     logout: (formData: UserLogoutForm): Promise<boolean> => {
        return new Promise<boolean>((resolve, reject) => {
            UserService.logout(formData).then(value => {
                if (value.success) {
                    Models.remove('userInfo')
                    removeHokageUid()
                    return resolve(true)
                }
                return reject(value)
            }).catch(err => {
                return reject(err)
            })
        })
     },

     register: (formData: UserRegisterForm): Promise<ServiceResult<UserRegisterForm>> => {
        return new Promise((resolve, reject) => {
            UserService.register(formData).then(value => {
				if (value.success && value.data) {
                    const data: UserRegisterForm = value.data
                    setHokageUid(data.id || 0)
					return resolve(value)
				}
				return reject(value)
			}).catch(err => {
				return reject(err)
			})
        })
     },

     listAllSubordinate: (): Promise<UserVO[]> => {
         return new Promise<UserVO[]>((resolve, reject) => {
             UserService.listAllSubordinate().then(value => {
                 if (value.success) {
                     return resolve(value.data)
                 }
                 reject(value.msg)
             }).catch(err => {
                 return reject("获取普通用户失败. err: " + JSON.stringify(err))
             })
         })
     },

     addSupervisor: (form: UserServerOperateForm): Promise<boolean> => {
         return new Promise<boolean>((resolve, reject) => {
             UserService.addSupervisor(form).then(value => {
                 if (value.success) {
                     return resolve(value.data)
                 }
                 reject(value.msg)
             }).catch(err => {
                 return reject("添加管理员失败. err: " + JSON.stringify(err))
             })
         })
     },

     listSubordinate: (supervisorId: number): Promise<UserVO[]> => {
         return new Promise<UserVO[]>((resolve, reject) => {
             const form: UserServerSearchForm = { id: supervisorId, username: '', label: '' }
             UserService.listSubordinate(form).then(value => {
                 if (value.success) {
                     return resolve(value.data)
                 }
                 reject(value.msg)
             }).catch(err => {
                 return reject("获取普通用户列表. err: " + JSON.stringify(err))
             })
         })
     },

     addSubordinate: (form: UserServerOperateForm): Promise<boolean> => {
         return new Promise<boolean>((resolve, reject) => {
             UserService.addSubordinate(form).then(value => {
                 if (value.success) {
                     return resolve(value.data)
                 }
                 reject(value.msg)
             }).catch(err => {
                 return reject("添加用户失败. err: " + JSON.stringify(err))
             })
         })
     },
     supervisorSearch: (form: OperatorSearchFormType): Promise<UserVO[]> => {
         return new Promise<UserVO[]>((resolve, reject) => {
             UserService.searchSupervisor(form).then(value => {
                 if (value.success) {
                     return resolve(value.data)
                 }
                 reject(value.msg)
             }).catch(err => {
                 return reject("搜索管理员失败. err: " + JSON.stringify(err))
             })
         })
     }
 }