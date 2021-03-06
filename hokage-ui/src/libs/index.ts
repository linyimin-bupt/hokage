import { Models } from './model'
import { Terminal } from 'xterm'
import { UserRegisterForm, UserRoleEnum } from '../axios/action/user/user-type'
import React from 'react'

export const queryString = () => {
  let _queryString: { [key: string]: any } = {};
  const _query = window.location.search.substr(1);
  const _vars = _query.split('&');
  _vars.forEach((v, i) => {
    const _pair = v.split('=');
    if (!_queryString.hasOwnProperty(_pair[0])) {
      _queryString[_pair[0]] = decodeURIComponent(_pair[1]);
    } else if (typeof _queryString[_pair[0]] === 'string') {
      _queryString[_pair[0]] = [_queryString[_pair[0]], decodeURIComponent(_pair[1])];
    } else {
      _queryString[_pair[0]].push(decodeURIComponent(_pair[1]));
    }
  });
  return _queryString;
}

/**
 * generate a random integer, inclusive max
 * @param max
 */
export const randomInt = (max: number): number => {
  max = Math.ceil(max)
  return Math.floor(Math.random() * (max + 1))
}

/**
 * calculate string hashcode
 * @param text
 */
export const hashCode = (text: string): number => {
  let hash = 5381
  let length = text.length
  while (length) {
    hash = (hash * 33) ^ text.charCodeAt(--length)
  }
  return hash >>> 0;
}



export const randomColor = (text: string): string => {
  const colorList: string[] = Models.get('serverLabelColor')
  return colorList[hashCode(text) % colorList.length]
}

export const setHokageUid = (id: number) => {
  if (!id) id = 0
  window.localStorage.setItem('hokageUid', id + '')
}

export const getHokageUid = (): number => {
  const hokageUid = window.localStorage.getItem('hokageUid')
  return hokageUid ? parseInt(hokageUid) : 0
}

export const removeHokageUid = () => {
  window.localStorage.removeItem('hokageUid')
}

export const setHokageRole = (role: number) => {
  if (!role) role = UserRoleEnum.subordinate
  window.localStorage.setItem('hokageRole', role + '')
}

export const getHokageRole = () => {
  const role = window.localStorage.getItem('hokageRole')
  return role ? parseInt(role) : UserRoleEnum.subordinate
}

export const removeHokageRole = () => {
  window.localStorage.removeItem('hokageRole')
}

export const setHokagePermissions = (permissions: string[]) => {
  window.localStorage.setItem('hokagePermission', JSON.stringify(permissions))
}

export const getHokagePermissions = (): string[] => {
  const permissions = window.localStorage.getItem('hokagePermission') || '[]'
  return JSON.parse(permissions)
}

export const removeHokagePermissions = () => {
  window.localStorage.removeItem('hokagePermission')
}

export const setHokageUserInfo = (userInfo: string) => {
  window.localStorage.setItem("userInfo", userInfo)
}

export const getHokageUserInfo = (): UserRegisterForm => {
  const info = window.localStorage.getItem("userInfo") as string
  return JSON.parse(info) as UserRegisterForm
}

export const removeHokageUserInfo = () => {
  window.localStorage.removeItem('userInfo')
}

// ssh连接加载动画
export const xtermSpinner = (terminal: Terminal): NodeJS.Timeout => {
  const spinner = {
    "interval": 100,
    "frames": [
      "🧑⚽️                🧑 ",
      "🧑 ⚽️               🧑 ",
      "🧑  ⚽️              🧑 ",
      "🧑   ⚽️             🧑 ",
      "🧑    ⚽️            🧑 ",
      "🧑     ⚽️           🧑 ",
      "🧑      ⚽️          🧑 ",
      "🧑       ⚽️         🧑 ",
      "🧑        ⚽️        🧑 ",
      "🧑         ⚽️       🧑 ",
      "🧑          ⚽️      🧑 ",
      "🧑           ⚽️     🧑 ",
      "🧑            ⚽️    🧑 ",
      "🧑             ⚽️   🧑 ",
      "🧑              ⚽️  🧑 ",
      "🧑               ⚽️ 🧑 ",
      "🧑                ⚽️🧑 ",
      "🧑               ⚽️ 🧑 ",
      "🧑              ⚽️  🧑 ",
      "🧑             ⚽️   🧑 ",
      "🧑            ⚽️    🧑 ",
      "🧑           ⚽️     🧑 ",
      "🧑          ⚽️      🧑 ",
      "🧑         ⚽️       🧑 ",
      "🧑        ⚽️        🧑 ",
      "🧑       ⚽️         🧑 ",
      "🧑      ⚽️          🧑 ",
      "🧑     ⚽️           🧑 ",
      "🧑    ⚽️            🧑 ",
      "🧑   ⚽️             🧑 ",
      "🧑  ⚽️              🧑 ",
      "🧑 ⚽️               🧑 ",
      "🧑⚽️                🧑 ",
    ]
  }
  let i = 0
  return setInterval(() => {
    const { frames } = spinner;
    // eslint-disable-next-line no-octal-escape
    terminal.write('\x1bc')
    terminal.writeln('connecting ' + frames[i = ++i % frames.length])
  }, spinner.interval)
}

export const emptyFunction = () => {}

export function hasPermissions(path: string): boolean {
  const isSuper = getHokageRole() === UserRoleEnum.super_operator
  const permissions = getHokagePermissions()
  if (!path && isSuper) {
    return true
  }
  for (let orItem of path.split('|')) {
    if (isSubArray(permissions, orItem.split('&'))) {
      return true
    }
  }
  return false
}

/**
 * 数组包含关系判断
 * @param parent
 * @param child
 */
export function isSubArray(parent: string[], child: string[]): boolean {
  for (let item of child) {
    if (!parent.includes(item)) {
      return false
    }
  }
  return true
}

export interface RouteParam {
  subPath: string,
  path?: string,
  component: any
}

export function createRoute(path: string, component: React.ReactNode) {
  return { subPath: path, component }
}

export function createModuleRoute(prefix: string, route: RouteParam[]) {
  return { prefix, route }
}

const sizeUnit: { [key: string]: number } = {
  'B' : 1,
  'KB': 1024,
  'MB': 1024 * 1024,
  'GB': 1024 * 1024 * 1024
}

export function transferHumanReadableSize2Byte(size: string) : number {
  const result = size.toUpperCase().match(/([0-9]+\.[0-9]+) (B|KB|MB|GB)/)
  if (result && result[1] && result[2]) {
    return parseFloat(result[1]) * (sizeUnit[result[2]] ? sizeUnit[result[2]] : 1)
  }
  return 0
}

export function range(start: number, end: number): number[] {
  const array = []
  for (let i = start; i < end; i++) {
    array.push(i)
  }
  return array
}

export function status2Color(status: number): string {
  if (status === 0) {
    return 'blue'
  }
  if (status === 1) {
    return 'green'
  }
  if (status === -1) {
    return 'red'
  }
  return 'red'
}
