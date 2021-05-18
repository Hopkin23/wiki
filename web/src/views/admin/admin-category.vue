<template>
    <a-layout>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <p>
                <a-form layout="inline">
                    <a-form-item>
                        <a-button type="primary" @click="handleQuery()">
                            查询
                        </a-button>
                    </a-form-item>
                    <a-form-item>
                        <a-button type="primary" @click="add()">
                            新增
                        </a-button>
                    </a-form-item>
                </a-form>
            </p>

            <a-table
                    :columns="columns"
                    :row-key="record => record.id"
                    :data-source="categorys"
                    :pagination="false"
                    :loading="loading">
                <!--渲染封面图片-->
                <template #cover="{ text: cover }">
                    <img v-if="cover" :src="cover" alt="avatar" />
                </template>
                <!--渲染按钮-->
                <template v-slot:action="{ text, record }">
                    <a-space size="small">
                        <a-button type="primary" @click="edit(record)">
                            编辑
                        </a-button>
                        <a-popconfirm
                                title="删除后不可恢复，确认删除?"
                                ok-text="是"
                                cancel-text="否"
                                @confirm="handleDelete(record.id)"
                        >
                            <a-button type="danger">
                                删除
                            </a-button>
                        </a-popconfirm>
                    </a-space>
                </template>
            </a-table>
        </a-layout-content>
    </a-layout>

    <!--  对话框内部套表单  -->
    <a-modal
            title="分类表单"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleModalOk"
    >
        <a-form :model="category" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="名称">
                <a-input v-model:value="category.name" />
            </a-form-item>
            <a-form-item label="父分类">
                <a-input v-model:value="category.parent" />
            </a-form-item>
            <a-form-item label="顺序">
                <a-input v-model:value="category.sort" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>



<script lang="ts">
    import {defineComponent, onMounted, ref} from 'vue';
    import axios from "axios";
    import {message} from 'ant-design-vue';
    import {Tool} from "@/util/tool";

    export default defineComponent({
        name: 'AdminCategory',
        components: {
        },
        setup() {
            const categorys = ref();

            //等待框
            const loading = ref(false);
            // 定义列
            const columns = [
                {
                    title: '名称',
                    dataIndex: 'name'
                },
                {
                  title: '父分类',
                  key: 'parent',
                  dataIndex: 'parent'
                },
                {
                    title: '顺序',
                    dataIndex: 'sort'
                },
                {
                    title: 'Action',
                    key: 'action',
                    slots: { customRender: 'action' }
                }
            ];

            /**
             * 数据查询
             **/
            const handleQuery = () => {
                loading.value = true;
                categorys.value = [];
                axios.get("/category/all").then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        categorys.value = data.content;
                    } else {
                        message.error(data.message);
                    }
                });
            };

            // -------- 表单 ---------
            const category = ref();
            const modalVisible = ref(false);
            const modalLoading = ref(false);
            const handleModalOk = () => {
                modalLoading.value = true;
                axios.post("/category/save", category.value).then((response) => {
                    modalLoading.value = false;// 只要后端有返回就不必loading
                    const data = response.data;
                    if (data.success) {
                        modalVisible.value = false;
                        modalLoading.value = false;

                        // 重写加载当前页列表
                        handleQuery();
                    } else {
                        message.error(data.message);
                    }
                });
            };

            /**
             * 编辑
             */
            const edit = (record: any) => {
                modalVisible.value = true;
                category.value =  Tool.copy(record);
            };

            /**
             * 新增
             */
            const add = () => {
                modalVisible.value = true;
                category.value = {};//清空
            };

            /**
             * 删除函数
             */
            const handleDelete = (id: string) => {
                axios.delete("/category/delete/"+id).then((response) => {
                    const data = response.data;
                    if (data.success){
                        // 重写加载当前页列表
                        handleQuery();
                    }
                });
            }
            // --------    ---------

            onMounted(() => {
                handleQuery();
            });

            return {
                // 表格
                categorys,
                columns,
                loading,
                handleQuery, // 内部函数调用无需return html界面调用需要return

                edit,
                add,

                //表单
                category,
                modalVisible,
                modalLoading,
                handleModalOk,

                handleDelete
            }
        }
    })
</script>

<style scoped>
    img {
        width: 50px;
        height: 50px;
    }
</style>
