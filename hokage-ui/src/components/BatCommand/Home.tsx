import React from 'react'
import BreadcrumbCustom, { BreadcrumbPrpos } from '../BreadcrumbCustom';
import MyBatCommand from './MyBatCommand';
import { Tabs } from 'antd';
import ExecutedBatCommand from './ExecutedBatCommand';

const breadcrumProps: BreadcrumbPrpos[] = [
  {
    name: '首页',
    link: '/app/index'
  },
  {
    name: '批量任务'
  }
]

export default class BatCommand extends React.Component {
  render() {
    return(
      <div>
        <BreadcrumbCustom breadcrumProps={breadcrumProps} />
        <Tabs type="card">
          <Tabs.TabPane tab="创建批量任务" key="create_bat_command">
            <MyBatCommand />
          </Tabs.TabPane>
          <Tabs.TabPane tab="已执行任务" key="view_bat_command">
            <ExecutedBatCommand />
          </Tabs.TabPane>
        </Tabs>
      </div>
    )
  }
}