package com.msaggik.thirdlessonpalettefordrawing10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ArtView extends View {

    // поля
    private Canvas canvas; // холст для рисования
    private Paint paint; // стиль рисования
    private Path path; // контур рисования
    private Bitmap bitmap; // контейнер, где будет храниться рисунок
    private final int PAINT_COLOR_DEF = 0xFF8E5DD5; // цвет по умолчанию

    // конструктор для кастомизации View
    public ArtView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // настройки отрисовки View
        path = new Path(); // объект контура

        paint = new Paint(); // объект стиля рисования
        paint.setAntiAlias(true); // сглаживание краёв
        paint.setStyle(Paint.Style.STROKE); // стиль кисти
        paint.setStrokeCap(Paint.Cap.ROUND); // вид концов рисуемых линий
        paint.setStrokeJoin(Paint.Join.ROUND); // стиль объединения рисуемых линий
        paint.setColor(PAINT_COLOR_DEF); // цвет линии
        paint.setStrokeWidth(30); // толщина рисуемой линии
    }

    // размер View (новая ширина, новая длина, старая ширина, старая длина)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // создание пустого изображения с заданной шириной, высотой и цветовой схемой (альфа, красный, зеленый, голубой)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        // создание холста для созданного изображения
        canvas = new Canvas(bitmap);
    }

    // метод рисования на холсте
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // определение рисунка на холсте
        canvas.drawBitmap(bitmap, 0, 0, paint); // помещение на холст отрисованных линий
        canvas.drawPath(path, paint); // показ отрисованных линий
    }

    // метод обработки касания View
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // извлечение координат касания View
        float x = event.getX();
        float y = event.getY();

        // обработка событий касания View
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // прикосновение к View
                path.moveTo(x, y); // определение начала контура
                path.lineTo(x, y); // задание линии контура
                break;
            case MotionEvent.ACTION_MOVE: // движение по экрану
                path.lineTo(x, y); // задание линии контура
                break;
            case MotionEvent.ACTION_UP: // отпускание экрана
                canvas.drawPath(path, paint); // отрисовка на хосте сгенерированных данных
                path.reset(); // прерывание линии
                break;
        }
        invalidate(); // обновление View
        return true;
    }

    // метод очистки кастомизированного View
    public void clear() {
        // очистка экрана и заполнение его белым цветом
        canvas.drawColor(0xFFFFFFFF, PorterDuff.Mode.CLEAR);
        // обновление представления View
        invalidate();
    }
}
