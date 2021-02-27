import { Button, Tag } from 'antd'
import { randomColor } from '../../../utils'
import React from 'react'
import { BreadcrumbPrpos } from '../../bread-crumb-custom'
import { Operation } from '../../../axios/action/user/user-type'

/**
 * @author linyimin
 * @date 2021/2/19 12:34 am
 * @email linyimin520812@gmail.com
 * @description
 */


export const nestedColumn = [
    {
        title: 'id',
        dataIndex: 'id',
        key: 'id'
    },
    {
        title: '主机名',
        dataIndex: 'hostname',
        key: 'hostname'
    },
    {
        title: '域名',
        dataIndex: 'domain',
        key: 'domain'
    },
    {
        title: '标签',
        dataIndex: 'labels',
        key: 'labels',
        render: (labels: string[]) => {
            return labels.map((tag: any )=> <Tag color={randomColor(tag)} key={tag}>{tag}</Tag>)
        }
    },
    {
        title: '使用人数',
        dataIndex: 'userNum',
        key: 'userNum'
    },
    {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        render: (text: string) => <Tag color = {randomColor(text)}> { text } </Tag>
    },
    {
        title: '操作',
        dataIndex: 'operationList',
        key: 'operationList',
        render: (operationList: Operation[]) => operationList.map(operation => <Button type="link" href={operation.operationLink}>{operation.operationName}</Button>)
    }
]

export const columns = [
    {
        title: 'id',
        dataIndex: 'id',
        key: 'id'
    },
    {
        title: '姓名',
        dataIndex: 'username',
        key: 'username'
    },
    {
        title: '负责服务器数量',
        dataIndex: 'serverNum',
        key: 'serverNum'
    },
    {
        title: '服务器标签',
        dataIndex: 'serverLabelList',
        key: 'serverLabelList',
        render: (serverLabelList: string[]) => serverLabelList.map(
            (tag: string)=> <Tag color={randomColor(tag)} key={tag}>{tag}</Tag>
        )
    },
    {
        title: '操作',
        dataIndex: 'operationList',
        key: 'action',
        render: (operationList: Operation[]) => operationList.map(operation => <Button type="link" href={operation.operationLink}>{operation.operationName}</Button>)
    }
]

export const breadcrumProps: BreadcrumbPrpos[] = [
    {
        name: '首页',
        link: '/app/index'
    },
    {
        name: '用户管理'
    },
    {
        name: '服务器管理员'
    }
]