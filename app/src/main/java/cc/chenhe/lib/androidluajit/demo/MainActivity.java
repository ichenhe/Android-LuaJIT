package cc.chenhe.lib.androidluajit.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private LuaState lua;
    private TextView tv;
    private String filePath;

    @Override
    protected void onDestroy() {
        if (lua != null)
            lua.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.text_view);
        filePath = getExternalFilesDir(null).getAbsolutePath();
        copyAssets(this, "lua", filePath);

        lua = LuaStateFactory.newLuaState();
        lua.openLibs();

        runLua();
    }

    private void runLua() {
        int r = lua.LdoFile(filePath + "/bytecode.lua");
        Log.i("Load", r + "");
        lua.getGlobal("setText");
        lua.pushJavaObject(tv);
        lua.pushString("Hello Lua");

        lua.pcall(2, 0, 0);
    }

    public void copyAssets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);
            if (fileNames.length > 0) {
                File file = new File(newPath);
                file.mkdirs();
                for (String fileName : fileNames) {
                    copyAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
