import {createStore, ModuleTree} from 'vuex'

const requireModule = require.context('@/store/', true, /\.ts$/,);

export default createStore({
  modules: requireModule.keys().reduce((result: any, fileName: string) : ModuleTree<any> => {
    let module;
    try {
      module = requireModule(fileName);
    } catch (e) {
      return result;
    }
    fileName = fileName.substr(fileName.lastIndexOf('/') + 1, fileName.length);
    if (!module.default || fileName === 'index.ts' || fileName === 'types.ts') {
      return result;
    }
    const moduleName = fileName.replace(/(\.\/|\.ts)/gi, '').replace(/^./, (str) => str.toUpperCase());
    result[moduleName] = module.default;

    return result;
  }, {})
})
