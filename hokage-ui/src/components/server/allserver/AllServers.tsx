import React, { ReactText } from 'react'
import { Tag, message, Table, Row, Col, Button, Divider } from 'antd';
import BreadcrumbCustom, { BreadcrumbPrpos } from '../../BreadcrumbCustom';
import { InfoCircleOutlined, SyncOutlined, PlusOutlined, MinusOutlined } from '@ant-design/icons';
import Search from './Search';
import AddServer from '../AddServer';
const columns = [
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
    dataIndex: 'domainName',
    key: 'domainName'
  },
  {
    title: '标签',
    dataIndex: 'serverTags',
    key: 'serverTags',
    render: (serverTags: any, _: any, __: any) => {
      return (
      <span>
        {
          serverTags.map((tag: any )=> {
            let color = ''
            let name = ''
            switch (tag) {
              case 'ordinaryServer':
                color = 'magenta'
                name = 'X86'
                break
              case 'gpuServer':
                color = 'red'
                name = 'GPU'
                break
              case 'intranetServer':
                color = 'green'
                name = '内网'
                break
              case 'publicNetworkServer':
                color = 'purple'
                name = '公网'
                break
              default:
                color = '#f50'
                name = '未知'
            }
            return (
              <Tag color={color} key={tag}>
                {name}
              </Tag>
            );
          })
        }
      </span>)
    }
  },
  { 
    title: '管理员',
    dataIndex: 'admin',
    key: 'admin'
  },
  { 
    title: '使用人数',
    dataIndex: 'numOfUser',
    key: 'numOfUser'
  },
  { 
    title: '状态',
    dataIndex: 'status',
    key: 'status',
    render: (text: string, _: any, __: any) => {
      let color: string = ''
      switch(text) {
        case '在线':
          color = 'green'
          break;
        case '掉线':
          color = 'red'
          break
        default:
          color = 'red'
          break
      }
      return (
        <Tag color = {color}> { text } </Tag>
      )
    }
  },
  { 
    title: '操作',
    dataIndex: 'action',
    key: 'action'
  }
]
type AllServerState = {
  selectedRowKeys: ReactText[],
  isModalVisible: boolean
}

const breadcrumProps: BreadcrumbPrpos[] = [
  {
    name: '首页',
    link: '/app/index'
  },
  {
    name: '我的服务器'
  },
  {
    name: '所有的服务器'
  }
]

export default class AllServer extends React.Component {
  
  state: AllServerState = {
    selectedRowKeys: [],
    isModalVisible: false
  }
  
  onFinish = (value: any) => {
    console.log(value)
  }

  resetFields = () => {
    console.log("reset")
  }

  onSelectChange = (selectedRowKeys: ReactText[], selectedRows: any[]) => {
    this.setState({ selectedRowKeys })
    // TODO: 从selectRows中获取选择的目标数据,然后进行相关操作
  }

  add = () => {
    this.setState({ ...this.state, isModalVisible: true })
  }

  delete = () => {
    alert("delete operators bat")
  }

  sync = () => {
    alert("sync operator")
  }

  onModalOk = (value: any) => {
    console.log(value)
    this.setState({ ...this.state, isModalVisible: false })
    message.loading({ content: 'Loading...', key: 'addUser' });
    setTimeout(() => {
      message.success({ content: 'Loaded!', key: 'addUser', duration: 2 });
    }, 2000);
  }

  onModalCancel = () => {
    this.setState({ ...this.state, isModalVisible: false })
    message.warning({ content: '添加用户已经取消!', key: 'addUser', duration: 2 });
  }

  render() {
    const data: any = []
    for (let i = 0; i < 5; i++) {
      const value = {
        key: i + 1,
        id: 'id_' + i,
        hostname: 'master_' + i + ".pcncad.club",
        domainName: 'name_' + i + ".pcncad.club",
        serverTags: ['ordinaryServer', 'gpuServer', "intranetServer", "publicNetworkServer"],
        admin: 'banzhe',
        numOfUser: i + 1,
        status: "在线",
        action: '指定管理员 | 申请 | 撤销管理员 | 修改管理员'
      }
      data.push(value)
    }
    const { selectedRowKeys, isModalVisible } = this.state
    const rowSelection = {
      selectedRowKeys,
      onChange: this.onSelectChange
    };

    return (
      <div>
        <BreadcrumbCustom breadcrumProps={breadcrumProps} />
        <Search onFinish={this.onFinish} clear={this.resetFields} />
        <div style={{ backgroundColor: '#FFFFFF', padding: '8px 12px' }}>
          <Row
            gutter={24}
            style={{ backgroundColor: '#e6f7ff', border: '#91d5ff' }}
          >
            <Col span={12} style={{ display: 'flex', alignItems: 'center' }}>
              <span>
                <InfoCircleOutlined
                  translate="true"
                  style={{ color: "#1890ff" }}
                />
                已选择{<span style={{ color: "blue" }}>{selectedRowKeys.length}</span>}项
              </span>
            </Col>
            <Col span={12} >
              <span style={{ float: 'right' }}>
                {
                  selectedRowKeys.length > 0 ? ([
                    <Button
                      icon={<MinusOutlined translate="true" />}
                      onClick={this.delete}
                    >
                      批量删除
                    </Button>,
                    <Divider type="vertical" />
                  ]) : (
                      null
                  )
                }
                <Button
                  icon={<PlusOutlined translate="true" />}
                  onClick={this.add}
                >
                  添加
                </Button>
                <AddServer onModalOk={this.onModalOk} onModalCancel={this.onModalCancel} isModalVisible={isModalVisible} />
                <span style={{ paddingLeft: '64px' }} >
                  <SyncOutlined
                    translate="true" onClick={this.sync}
                  />
                </span>
              </span>
            </Col>
          </Row>
          <Table
            rowSelection={rowSelection}
            columns={columns}
            dataSource={data}
          />
        </div>
      </div>
    )
  }
    
} 