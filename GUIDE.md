Для создания compose-экранов можно использовать следующие компоненты:

- **Навигация по экранам** - `NavigationBar` + `NavigationBarItem`  
  (https://developer.android.com/develop/ui/compose/components/navigation-bar?hl=ru)

- **Выпадающий список** - `ExposedDropdownMenuBox`  
 (https://developer.android.com/reference/kotlin/androidx/compose/material3/ExposedDropdownMenuBox.composable)

- **Обертки**
  По желанию можно использовать обертки над compose-функциями. Это позволяет:
  - **Единый дизайн** - все кнопки выглядят одинаково, не нужно прописывать `colors`, `shape`, `elevation` каждый раз.
  - **Гибкость** - есть настройки под разные сценарии: кнопка с иконкой, опасная кнопка, заблокированная кнопка.
  - **Переиспользуемость** - если надо поменять стиль кнопок, правите один файл, и изменения применяются везде.
  - **Чистый код** - не дублировать настройки стилей по всему проекту.
 ```kotlin
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
